# tcp-file-transfer
Networking course lab 2

To build and run the application, you need Java 17+.

<h1>Server</h1>
<h2>Building</h2>

```bash
server/gradlew jar
```
<h2>Running</h2>

```bash
java -jar server/build/libs/server-1.0.jar --port 8080
```

You can set up any port you want.

<h1>Client</h1>
<h2>Building</h2>

```bash
client/gradlew jar
```
<h2>Running</h2>

```bash
java -jar client/build/libs/client-1.0-SNAPSHOT.jar --host 192.168.0.2 --port 8080 --file example.txt
```

Possible options:
<ol>
  <li>--host: server address</li>
  <li>--port: server port</li>
  <li>--file: path to file to be uploaded to server</li>
</ol>
