# h2gis-custom-functions-test

```
CREATE ALIAS SOGIS_Dummy FOR "ch.so.agi.h2.functions.MyBuffer.dummy";
```


```
java -jar /Users/stefan/apps/ili2h2gis-4.4.5/ili2h2gis-4.4.5.jar --dbfile bauzonengrenzen --nameByTopic --disableValidation --defaultSrsCode 2056 --strokeArcs --models SO_ARP_Bauzonengrenzen_20210120 --modeldir ".;http://models.geo.admin.ch" --doSchemaImport --import bauzonengrenzen_2021-01-27.xtf
```