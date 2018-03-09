# Groovy Tutorials


## Loops

Loop a range (both inclusive).
```groovy
for ( i in 1..10 ) {
	println i
}
```

Loop a range (upper exclusive).
```groovy
for ( i in 0..<10 ) {
	println i
}
```

Loop a list.
```groovy
for ( i in [2, 3, 5, 7, 11] ) {
	println i
}
```

Loop a map.
```groovy
for ( entry in [a:1, b:2, c:3] ) {
	println entry.value
}
```

Closure style looping. The collection being iterated is returned.
```groovy
[2, 3, 5, 7, 11].each { println it }
[2, 3, 5, 7, 11].each { prime -> println prime }
[2, 3, 5].eachWithIndex { p, i -> println "$i: $p" }
[a:1, b:2, c:3].each { println "key: ${it.key}, value: ${it.value}" }
[a:1, b:2, c:3].each { k, v -> println "key: $k, value: $v" }
```


## Strings

GString: ${expression}.
```groovy
value = 6
println "The value is $value."
```

Escaping
```groovy
def animal = 'a dog';

def str1 = 'single-quoted $animal \t \\t'; // literal, no interpolation, escape-required, no multiline
def str2 = "double-quoted $animal \t \\t"; // interpolation, escape-required, no multiline
def str3 = "double-quoted ${animal.toUpperCase()}"; // with method call
def str4 = '''triple-quoted
  $animal \t \\t''';    // literal, no interpolation, escape required, multiline
def str5 = /slashy string 
  $animal \t \\t/;    // interpolation, no escape required, multiline, useful for regex (many backslash)
def str6 = $/dollar slashy string 
  $animal \t \\t/$;    // interpolation, no escape required, multiline, useful for regex with html/xml tags (backslash with forward slash)
```

Substrings
```groovy
text = "Hello World!"
text[0]	// Returns "H"
text[0..4]	// Returns "Hello"
text[0..<4]	// Returns "Hell"
text[-1]	// Returns "!" (last char)
text[-6..-1]	// Returns "World!" (last 6th to last char)
```

Tokenizing. 
```groovy
"Hello  World!".tokenize(" ") // Returns [“Hello”, “World!”]. 

// Note that using split(” “) in this case (where there are 2 spaces between Hello and World!) 
// will result in 3 elements [“Hello”, “”, “World!”].
```


## Regex

```groovy
"Hello World!" ==~ /.*World.*/	// true

matcher = ( "email@address.com" =~ /(\w+)@([\w\.]+)/ )
matcher[0][1]    // Returns "email"
matcher[0][2]    // Returns "address.com"
```


## Files

Iterate each file in a directory. it is a File object.
```groovy
new File("d:/").eachFile { println it }
```

Match certain files in directory. See regular expression.
```groovy
new File("d:/").eachFileMatch(~/.*\.java/) { println it }
```

Iterate all files in directory recursively.
```groovy
new File("d:/").eachFileRecurse { println it }
```

Read whole text file.
```groovy
new File("test.txt").getText()
```

Read text file line by line.
```groovy
new File("test.txt").eachLine { println it }
```

Write text to file.
```groovy
new File("test.txt").withWriter { out-> out.println("Hello World!") }
```


## Collections

Basic operations
```groovy
list = []	// new empty list
list = [2, 3, 5, 7, 11]	// new initialized list
list[3]	// Returns 7.
list[-1]	// Returns 11 (last item)
list[1..2]	// Returns [3, 5] (sublist)
[2, 3] << 5	// append 5 to list. Returns [2, 3, 5]
[2, 3] + 5	// append. same as above.
[2, 3, 5, 7] - 3	// remove 3 from list. Returns [2, 5, 7]
list.remove(1)	// remove item 1 (integer 3) from list. Removed item is returned.

map = [:]
map = [a:1, b:2, c:3]
map["b"]	// returns 2
map.c	// returns 3
map["b"] = 5	// map.put("b", 5")
```

Find first item matching closure. Returns 7.
```groovy
[2, 3, 5, 7, 11].find { it > 6 }
```

Find all items matching closure. Returns [7, 11].
```groovy
[2, 3, 5, 7, 11].findAll { it > 6 }
```

Operate on each element. Returns [3, 4, 6, 8].
```groovy
[2, 3, 5, 7].collect { it+1 }
```

Join elements into String. Returns “2 3 5 7”.
```groovy
[2, 3, 5, 7].join(" ")
```


## Date/Time

Basic operations
```groovy
d = new Date()	// Today
++d	// Tomorrow (d++ behaves consistently like primitives)
--d	// Yesterday (d-- behaves consistently like primitives)
d+=2	// 2 days later
d.format("dd-MM-yyyy")	// date formatting
```


## JSON

```groovy
def obj = new groovy.json.JsonSlurper().parseText(json);
println obj.subobject;
println obj.subarray[1];
```


## SQL

Connect
```groovy
def sql = groovy.sql.Sql.newInstance('jdbc:url', 'user', 'pass', 'driver.classname');
```

Select
```groovy
sql.eachRow('SELECT colname FROM table WHERE param1=? AND param2=?', [param1, param2]) { row ->
  println row.colname
}
```

Insert/Update/Delete
```groovy
sql.execute('INSERT INTO TABLE VALUES(?, ?)', [param1, param2]);
```

Batch
```groovy
sql.withBatch(50, 'INSERT INTO TABLE VALUES(?, ?)') { stmt ->
  // some loop 
  params.each {param1, param2 -> 
    stmt.addBatch([param1, param2])
  }
}
```

One-row
```groovy
def row = sql.firstRow('SELECT colname FROM table WHERE param1=? AND param2=?', [param1, param2]);
println row.colname;
```

PostgreSQL import
```groovy
@GrabConfig(systemClassLoader=true)
@Grab(group='org.postgresql', module='postgresql', version='9.4-1205-jdbc42')
```

MySQL import
```groovy
@GrabConfig(systemClassLoader=true)
@Grab('mysql:mysql-connector-java:5.1.6')
```


## Threading

Run a thread.
```groovy
thread = Thread.start { doSomething(); }
```


## Networking

Grab content from URL. Operations are identical to stream manipulation.
```groovy
new URL("http://www.google.com/").getText()
```

Connect via TCP.
```groovy
new Socket("host", 23).withStreams { inn, out -> }
```

Proxy environment
```groovy
System.getProperties().put("proxySet", "true");
System.getProperties().put("proxyHost", "192.168.0.1");
System.getProperties().put("proxyPort", "8080");
```


## GUI – SwingBuilder
Below is an copy-style example of what can be done. Refer to the Widgets List for more documentation. Most bean-style attributes can be directly set in the constructor.
```groovy
import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout as BL;
swing = new groovy.swing.SwingBuilder();

frame = swing.frame(title:'Frame', defaultCloseOperation:JFrame.DISPOSE_ON_CLOSE, bounds:[100, 100, 800, 600], visible:true) {
    lookAndFeel("system")
    borderLayout()
    panel(constraints:BL.NORTH) {
        flowLayout(alignment:FlowLayout.LEFT)
        label(text:"Country: ")
        countryCombo = comboBox(items:["Japan", "China"], selectedIndex:0, actionPerformed: { changeValues(); } )
        label(text:"Area: ")
        areaCombo = comboBox(items:areas, selectedIndex:0, actionPerformed: { changeValues(); } )
    }
    plate = panel(constraints:BL.CENTER) {
        gridLayout(rows:10, cols:20)
        200.times {
            label(text:String.valueOf(it+1), horizontalAlignment:JLabel.CENTER, border:lineBorder(color:Color.LIGHT_GRAY), opaque:true)
        }
    }
}
```
From here you can see how to create JFrames, BorderLayout, GridLayout, JPanel, JLabel, JComboBox, Borders, ActionListeners, and how to set their attributes.


## Misc

Object inspection
```groovy
class Man {
    def name
    def age
    
    Man(name, age) {
        this.name = name;
        this.age = age;   
    }
}
john = new Man("John", 40)
john.dump()
```

Shorthand for Math.pow().
```groovy
2**3	// Returns 8.
```

Use ?. notation to help get around NPEs.
```groovy
[1, 2, null, 4].each { println it?.class }
```
