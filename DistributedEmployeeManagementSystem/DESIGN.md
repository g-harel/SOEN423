# Distributed Employee Management System (WebServices)

![](https://user-images.githubusercontent.com/9319710/48109509-c27d9200-e215-11e8-95d4-66a8d452226e.png)

### Server

The `Server` is started as a given location using the first command-line argument as location code. It first validates the location to make sure that it is expected and terminates if not. Using this location, the server creates an instance of `AddressBook` which is aware of all location's names and assigned ports. It then creates an instance of a `RecordServer` which handles all UDP communication (using the information from `AddressBook`) and stores all of the location's records using a `RecordStore`. This `RecordServer` is then given to the `LocationWS` which is published as a webservice endpoint.

Data validation is done inside the `Location`'s methods, before any actions are taken on the `RecordServer`'s data.

All server logs are sent to both stdout and a log file corresponding to the server's location.

### Client

The `Client` is started using an HR manager's ID as the first command-line argument. After validating this ID, the webservice endpoint is discovered and bound to the `ILocationWS` interface. This object is then passed along to the `InteractiveClient` which handles user input and validation.

All client logs are sent to both stdout and a log file corresponding to the HR manager's ID.

### Concurrency

The class handling record storage (`RecordStore`) has `synchronized` methods to ensure there are no data-accessing methods being executed on the data at the same time.

UDP message handling operations are processed in series since each server receives packets from a single port (OS buffers packets received while processing).

Similarly, each server's UDP sending operations also share the same port and behave in the same sequential manner.

## Test Scenarios

To facilitate testing, this project includes helper scripts to reliably generate code, compile the java source, run required setup, start the servers, run interactive/automated clients and clean up before exiting. Interactive clients allow testing the details of the validation and the automated clients make it possible to quickly verify the behavior of the system with complex concurrent scenarios.

Run interactive client:

```
./run ws client {{HR manager ID}}
```

Run automated scenario:

```
./run ws test
```

Run solo client (parallel to regular client):

```
./run ws solo {{HR manager ID}}
```

Both client and server logs are configured to be written to `./bin/log`.

To test how the system handles missing server(s), `run_server` commands can be removed from `./scripts/servers`.
