# Distributed Employee Management System (CORBA)

![423-page-2](https://user-images.githubusercontent.com/9319710/47477022-18ebd900-d7f1-11e8-8e54-7f80bb6550c1.png)

### Server

The `Server` is started as a given location using the first command-line argument as location code. It first validates the location to make sure that it is expected and terminates if not. Using this location, the server creates an instance of `AddressBook` which is aware of all location's names and assigned ports. It then creates an instance of a `RecordServer` which handles all UDP communication (using the information from `AddressBook`) and stores all of the location's records using a `RecordStore`. This `RecordServer` is then given to the `LocationImpl` which is bound to the ORB and exposed through the CORBA naming service.

Data validation is done inside the `LocationImpl`'s methods, before any actions are taken on the `RecordServer`'s data.

All server logs are sent to both stdout and a log file corresponding to the server's location.

### Client

The `Client` is started using an HR manager's ID as the first command-line argument. After validating this ID, It attempts to fetch a reference to the location object from the CORBA naming service. This is then passed along to the `InteractiveClient` which handles user input and data validation.

All client logs are sent to both stdout and a log file corresponding to the HR manager's ID.

### Concurrency

The class handling record storage (`RecordStore`) has `synchronized` methods to ensure there are no data-accessing methods being executed on the data at the same time.

UDP message handling operations are processed in series since each server receives packets from a single port (OS buffers packets received while processing).

Similarly, each server's UDP sending operations also share the same port and behave in the same sequential manner.

## Test Scenarios

To facilitate testing, this project includes helper scripts to reliably compile the `.idl` interface, compile the java source code, start the orbd, start the servers, run interactive/automated clients and clean up before exiting. Interactive clients allow testing the details of the validation and the automated clients make it possible to quickly verify the behavior of the system with complex concurrent scenarios.

Run interactive client:

```
./run/client {{HR manager ID}}
```

Run automated scenario:

```
./run/test
```

Both client and server logs are configured to be written to `./bin/log`.

To test how the system handles missing server(s), `run_server` commands can be removed from `./run/servers`.
