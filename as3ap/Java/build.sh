#!/bin/sh

CLASSPATH=
#:${JAVA_HOME}/lib/tools.jar

TARGET_CLASSPATH=`echo ./lib/*.jar | tr ' ' ':'`

TARGET_CLASSPATH=${TARGET_CLASSPATH}:${JAVA_HOME}/lib/tools.jar

ANT_HOME=.
ANT=$ANT_HOME/bin/ant
#xerces/xalan for test support.
JAXP_DOM_FACTORY="org.apache.xerces.jaxp.DocumentBuilderFactoryImpl"
JAXP_SAX_FACTORY="org.apache.xerces.jaxp.SAXParserFactoryImpl"

ANT_OPTS="$ANT_OPTS -Djavax.xml.parsers.DocumentBuilderFactory=$JAXP_DOM_FACTORY"
ANT_OPTS="$ANT_OPTS -Djavax.xml.parsers.SAXParserFactory=$JAXP_SAX_FACTORY"

    export ANT ANT_HOME ANT_OPTS


    exec $ANT $ANT_OPTIONS "$@"
