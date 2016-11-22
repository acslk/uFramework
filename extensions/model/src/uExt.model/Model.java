package uExt.model;

import java.lang.reflect.Field;
import java.util.*;

public class Model
{
    private static class ModelAnyValue
    {
    }

    /* statics fields */
    public final static Object ANY_VALUE = new ModelAnyValue();
    private final static String DEFAULT_TABLE_NAME = "Default";
    private static final Map<String, List<Model>> tables = new HashMap<>();
    private static final Map<String, Class<?>> tableTypes = new HashMap<>();

    /* implementation */
    private static List<String> extractPropertyNames(Class<?> clazz)
    {
        final List<String> propertyNames = new ArrayList<>();
        final Field[] fields = clazz.getFields();
        for (final Field field : fields)
        {
            // get all public fields that belong to
            // the child class
            if (!field.getDeclaringClass().equals(Model.class))
            {
                propertyNames.add(field.getName());
            }
        }
        return propertyNames;
    }

    private static List<Class<?>> extractPropertyTypes(Class<?> clazz)
    {
        final List<Class<?>> propertyAttributes = new ArrayList<>();
        final Field[] fields = clazz.getFields();
        for (final Field field : fields)
        {
            // get all public fields that belong to
            // the child class
            if (!field.getDeclaringClass().equals(Model.class))
            {
                propertyAttributes.add(field.getType());
            }
        }
        return propertyAttributes;
    }

    private static List<Field> extractPropertyFields(Class<?> clazz)
    {
        final List<Field> propertyFields = new ArrayList<>();
        final Field[] fields = clazz.getFields();
        for (final Field field : fields)
        {
            // get all public fields that belong to
            // the child class
            if (!field.getDeclaringClass().equals(Model.class))
            {
                propertyFields.add(field);
            }
        }
        return propertyFields;
    }

    public Model()
    {
        final Class<?> clazz = this.getClass();
        final List<String> fields = extractPropertyNames(clazz);

        if (fields.size() == 0)
        {
            throw new RuntimeException("No fields detected");
        }

        // create a new default table for the object
        if (!tables.containsKey(getDefaultTableName(this.getClass())))
        {
            createTable(clazz, getDefaultTableName(this.getClass()));
        } else
        {
            // if already exists, needs to make sure that they share the same schema
            if (!tableTypes.get(getDefaultTableName(this.getClass())).equals(this.getClass()))
            {
                throw new RuntimeException("Attempt to create same table with different schema");
            }
        }
    }

    private static String getDefaultTableName(Class<?> clazz)
    {
        return clazz.getSimpleName() + "_" + DEFAULT_TABLE_NAME;
    }

    private static boolean validateTableTypes(List<Class<?>> types, Object[] objs)
    {
        if (types.size() != objs.length)
        {
            return false;
        }

        for (int i = 0; i < objs.length; i++)
        {
            final Object obj = objs[i];

            if (!(obj == null || obj instanceof ModelAnyValue))
            {
                // null matches any datatype
                // or ignore
                if(!obj.getClass().equals(types.get(i)))
                    return false;
            }
        }
        return true;
    }

    private static boolean matchConstraint(Model model, Object[] constraints) throws IllegalAccessException
    {
        if (model == null)
        {
            // bug check
            throw new RuntimeException("Internal Error: Data Row is null");
        }

        final List<Field> fields = extractPropertyFields(model.getClass());

        for (int i = 0; i < fields.size(); i++)
        {
            final Object eachObj = fields.get(i).get(model);
            if (!(constraints[i] instanceof ModelAnyValue))
            {
                // can simplify logic here but would be harder to read
                if (eachObj == null)
                {
                    // if eachObj is null, then the constraint must be null
                    if (constraints[i] != null)
                        return false;
                } else
                {
                    // else just check if they are equal
                    if (!eachObj.equals(constraints[i]))
                        return false;
                }
            }
        }
        return true;
    }

    public static synchronized List<Model> get(String tableName, Object... constraints) throws IllegalAccessException
    {
        final List<Model> table = tables.get(tableName);
        final Class<?> tableType = tableTypes.get(tableName);
        if (table == null)
        {
            throw new RuntimeException("Table " + tableName + " does not exist");
        }

        final List<Class<?>> types = extractPropertyTypes(tableType);

        // validate data types
        if (!validateTableTypes(types, constraints))
        {
            throw new RuntimeException("Invalid constraints");
        }

        final List<Model> result = new ArrayList<>();
        // traverse the data and

        for (final Model model : table)
        {
            if (matchConstraint(model, constraints))
            {
                result.add(model);
            }
        }

        // both not null
        return result;
    }

    public static synchronized <T extends Model> List<T> get(Class<T> clazz, Object... constraints) throws IllegalAccessException
    {
        List<? extends Model> ret = get(getDefaultTableName(clazz), constraints);
        return (List<T>)ret;
    }

    public static synchronized void insert(String tableName, Model obj)
    {
        if(!tables.containsKey(tableName))
        {
            throw new RuntimeException("Table " + tableName + " does not exist.");
        }

        // validate obj
        if (obj.getClass() != tableTypes.get(tableName))
        {
            throw new RuntimeException("Invalid model type");
        }

        tables.get(tableName).add(obj);
    }

    public static synchronized void insert(Model obj) throws IllegalAccessException
    {
        insert(getDefaultTableName(obj.getClass()), obj);
    }

    public static synchronized void delete(String tableName, Model obj)
    {
        if(!tables.containsKey(tableName))
        {
            throw new RuntimeException("Table " + tableName + " does not exist.");
        }

        // validate obj
        if (obj.getClass() != tableTypes.get(tableName))
        {
            throw new RuntimeException("Invalid model type");
        }

        tables.get(tableName).remove(obj);
    }

    public static synchronized void delete(Model obj)
    {
        delete(getDefaultTableName(obj.getClass()), obj);
    }

    public synchronized static void deleteTable(String tableName)
    {
        if (!tables.containsKey(tableName))
        {
            throw new RuntimeException("Attempt to delete non-existing table.");
        } else
        {
            tables.remove(tableName);
            tableTypes.remove(tableName);
        }
    }

    public synchronized static void createTable(Class<?> clazz, String tableName)
    {
        if (!tables.containsKey(tableName))
        {
            tables.put(tableName, new ArrayList<>());
            tableTypes.put(tableName, clazz);
        } else
        {
            throw new RuntimeException("Attempt to create duplicate table.");
        }
    }
}
