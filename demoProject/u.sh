if [ $1 = "build" ]; then
	rm -r build 2> /dev/null
	mkdir build
	javac lib/src/Main.java -d build
elif [ $1 = "run" ]; then
	java -cp build Main
else
	echo "usage: ./u.sh build or ./u.sh run"
fi