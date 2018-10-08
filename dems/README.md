# Distributed Employee Management System (DEMS) using Java RMI

The system is separated into three packages: server, common and client. The common package handles all common logic like the RMI interface, domain objects, validation and logging. The server package starts or connects to an RMI repository and and binds an implementation of the RMI interface to it. The client package discovers its associated server using the manager id's location token. It then offers a barebones CLI to run operations on this server. Both server and client also log their proceedings to a file int the `./log` directory.

![image](https://user-images.githubusercontent.com/9319710/46591662-33e8ea00-ca8a-11e8-9d0c-89c9dfda54a3.png)

### server

When a server starts with a given location, it first attempts to create a registry at a defined port. If the registry already exists, it will instead transparently use it. Next, it binds itself to this registry at an address informed by the location token it was started with. It then attempts to lookup all other servers using the list of valid locations. The list of working peers is kept for future use and will be retried when necessary. Local operations are handled in a predictable way, inserts and edits modify the stored hash map of records (indexed with the first letter of the record's last name). However, when an operation requires the involvement of one of the other servers, it uses the stored list of peers to pass along the data. All operations are logged to the console and to a file when they are received and when they complete. To ensure that operations can safely be invoked concurrently, the `synchronized` keyword is used for all methods that modify data in the object.

### client

When the client is started with a specific manager id, it attempts to lookup the correct server associated with that manager id's location token. It then starts a looping prompt for commands from the client's user. Multiple command examples are implemented, which can be listed using the `help` command. Once the user issues a command, it is logged to the console/file and always sent to the server with the same location as the client.

# Test Scenarios

### Create manager record.

* Start `UK` server.
* Start `UK` client.
* Create manager record.

### Create employee record.

* Start `CA` server.
* Start `CA` client.
* Create employee record in `CA`.

### Create employee record.

* Start `US` server.
* Start `US` client.
* Create employee record in `UK`.

### Get record count for partial list of servers.

* Start `CA` server.
* Start `CA` client.
* Create manager record.
* Get record count.

### Get record count for complete list of servers.

* Start `CA` server.
* Start `US` server.
* Start `UK` server.
* Start `US` client.
* Create employee record in `CA`.
* Create employee record in `US`.
* Create employee record in `US`.
* Get record count.

### Edit manager record data.

* Start `UK` server.
* Start `UK` client.
* Create manager record.
* Edit manager record.

### Edit employee record.

* Start `CA` server.
* Start `CA` client.
* Create employee record in `CA`.
* Edit employee record.
