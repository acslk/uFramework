#!/usr/bin/env bash
ant clean
java -cp lib/u.jar:lib/commons-io-2.5/commons-io-2.5.jar:lib/netty/netty-all-4.1.6.Final.jar u.script.PreprocessProject
ant build