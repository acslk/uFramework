package uExt.routing;

import io.netty.handler.codec.http.HttpRequest;
import u.script.InitPaths;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Weilong on 2016-11-21.
 */
public class Routing {
    ArrayList<String> routes = new ArrayList<>();
    public void readConfig(){

            //read from route file
            File routeConfig = new File(InitPaths.PROJECT_APP_DIR, "controllers/routes");
            BufferedReader reader = null;
            String curLine = "";
            try{
                reader = new BufferedReader(new FileReader(routeConfig));
                while((curLine = reader.readLine()) != null ){
                    if(curLine.length()>0 && curLine.charAt(0) !='#'){// skip blank line && comment
                        routes.add(curLine);
                    }
                }
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if (reader!= null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    private void processingRoute(String httpReq){
        //for each request go thru the routes list to find a match
        Pattern pattern = Pattern.compile("([^(http)(https)])([^/]+)");
        Pattern typePattern = Pattern.compile("\\[(.*?)\\]");
        String handler = "";
        String paramType ="";
        String param = "";

        //go thru routes to find a match
        for(String route: this.routes){
            String[] substrings = route.split("\\s+");
            String urlConfig = substrings[1];
            //get the type of parameter if exits
            Matcher typeMatcher =typePattern.matcher(substrings[2]);
            if (typeMatcher.find()) paramType = typeMatcher.group(1);
            Matcher configMatcher = pattern.matcher(urlConfig);
            Matcher reqMatcher = pattern.matcher(httpReq);
            int groupCnt = 0; // count amt of groups that have been processed
            boolean matched = false;

            while(reqMatcher.find() && configMatcher.find()){
                for(int i = 0 ; i <= reqMatcher.groupCount(); i++){
                    System.out.println(reqMatcher.group(i)+ " " + configMatcher.group(i));
                }
                if(reqMatcher.group(2).equals(configMatcher.group(2))){
                    groupCnt++;
                    if(!reqMatcher.find() && !configMatcher.find()){
                        matched = true;
                        break;
                    }
                }else if(groupCnt != 0 && reqMatcher.group(2).contains(":") && configMatcher.group(2).contains(":") ){
                    matched = true;
                    param = reqMatcher.group(2).replaceAll("(:)","");
                    break;
                }else{
                    break;
                }
            }
            if(matched){
                handler = substrings[2].replaceAll("\\(.*", "");
                break;
            }
        }

        if(handler.equals("")){
            System.out.println("ERROR: no match for current request, please verify route.conf");
            return;
        }
        //call the handler
        executeHandler(handler, param, paramType);
    }

    private static void executeHandler(String handlerCode, String param, String paramType){
        System.out.println("Code to execute: "+ handlerCode);
        //prepare parameters
        Class noparams[] = {};
        //String parameter
        Class[] paramString = new Class[1];
        paramString[0] = String.class;

        //int parameter
        Class[] paramInt = new Class[1];
        paramInt[0] = Integer.TYPE;


        String[] strs = handlerCode.split("\\.");
        String methodName = strs[strs.length-1];
        int idx = handlerCode.lastIndexOf(".");
        String classPath = handlerCode.substring(0, idx);

        try {// NOT ALLOWED to include class from test code
            Class c = Class.forName(classPath);
            System.out.println("Class found = " + c.getName());
            Object obj = c.newInstance();
            Method method = null;
            //invoke method
            if(!param.equals("") && !paramType.equals("")){
                if(paramType.equals("Integer")){
                    method = c.getDeclaredMethod(methodName,paramInt);
                    method.invoke(obj, Integer.parseInt(param));
                }else{ // String
                    method = c.getDeclaredMethod(methodName,paramString);
                    method.invoke(obj, param);
                }
            }else{
                method = c.getDeclaredMethod(methodName, noparams);
                method.invoke(obj,null);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void routing(HttpRequest httpRequest){
        if(routes.size() == 0 ){
            readConfig();
        }// read route file if routes =  empty

        String reqUrl = httpRequest.uri();
        //process url
        assert(!reqUrl.equals(""));
        processingRoute(reqUrl);
    }
}
