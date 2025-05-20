# Prog_lab6

for installation and first go (on helios linux use **export** for enviromental variables, in windows cmd use **set**)

```
git clone https://github.com/isa_alex/Prog_lab6/ 
mvn package
export file_path=/home/studs/sXXXXXX/Prog_lab6/server/my_objects.xml
```
```
set file_path=C:\~\Prog_lab6\server\my_objects.xml
```

first cmd window

```
cd server/target
java -jar server-1.0-SNAPSHOT.jar
```

in another cmd window
```
cd client/target
java -jar client-1.0-SNAPSHOT.jar localhost 6086
```
script running for client
```
execute_script /home/studs/sXXXXXX/Prog_lab6/client/script
```
