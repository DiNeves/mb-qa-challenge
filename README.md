# Mercedes-Benz.io Shop Australia QA Challenge

This is the test suite for the MB.io QA Challenge that executes automated tests in the [Mercedes-Benz Shop Australia]([https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo/]) website, in which were used the following technologies to run those: Java, Maven, Selenium and TestNG library.

### Requirements

Please ensure you have installed the following software and these specific versions:

- Java JDK 17.0.9 LTS;
- Maven 3.9.5.

The IDE used for coding was Eclipse (4.29.0).

-- Maven Dependencies
- TestNG (7.8.0);
- Selenium (4.14.1).

-- Maven Plugins
- Maven Compiler (3.11.0);
- Maven Surefire (3.2.1).

### Test Setup

For configuring and parameterizing the tests with different values, please edit the file `src/test/resources/TestSuites/ShopHomePageTests.xml`.

The first part of the xml file contains the values that are used for the automated Selenium tests.
Example below:

```xml
	<parameter name="appUrl" value="https://shop.mercedes-benz.com/en-au/shop/vehicle/srp/demo/" />
	
	<parameter name="selectedStateLocation" value="New South Wales" />
	<parameter name="insertedPostalCode" value="2007" />
	<parameter name="expectedRadioBoxText" value="Private" />	
	
	<parameter name="sortingFilterType" value="Price (descending)" />	
	
	<parameter name="exportFilePath" value="./test-file-output/output.txt" />
```

The second part contains 2 test cases that run in diferent browsers (Chrome and FireFox).


### Running the Tests

To run the test, please execute the following command in the workspace's root directory of the project.

```bash
mvn clean install
mvn clean test
```

### Test Results

The exported data can be found on the file configured at `src/test/resources/TestSuites/ShopHomePageTests.xml`.

```xml
<parameter name="exportFilePath" value="./test-file-output/output.txt" />
```

```txt
Model Year:2019;VIN:WDC2539462V148681;
```

The TestNG plugin generates an HTML report file that can be found at `test-output/index.html`.

## License

This project is licensed under the MIT License.
