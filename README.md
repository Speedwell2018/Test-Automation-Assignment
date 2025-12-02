This project automates testing for the Android Sauce Labs Mobile Sample app using Appium with Java and TestNG.
Tests run on an Android emulator with Maven for build and dependency management.

Requirements:
- Java: JDK 21, Java configured in your PATH
- Maven: Installed and configured in your PATH
- Node.js (v.20.x) and npm(v. 11.x): Installed (required to run Appium server)
- Appium: Version 3.1.1 installed globally via npm
- Android SDK: Configured with adb accessible in PATH



1. Download app https://github.com/saucelabs/sample-app-mobile/releases/.

2. Create .env file in the root directory for environment-specific variables. 
   A .env.example file is provided for reference. Add full path to application in .env file.

3. Open a terminal and run: appium.
   Ensure the server starts successfully and listens on the default port (4723).

4.Run your preferred Android emulator: emulator -avd <your_avd_name>

5. Confirm the device is running and available with:adb devices

6. Navigate to the project root directory and run:mvn clean test
   This command will compile the project and execute all tests.

7. Test results and logs will be output in the standard Maven target/ folder.