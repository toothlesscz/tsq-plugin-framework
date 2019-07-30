# Teamspeak Query Plugin Framework

A java-based plugin framework for Teamspeak 3 servers.

## Features
- Setup wizard for easy-to-use installation
- Detailed log files and console output
- Can be configured to automaticall reconnect to query after connectivity problems

## Getting Started

### Prerequisites

#### Cross-Platform
- Java 8 or newer
- Teamspeak 3/5 server with query access

#### Windows
No additional software required.

#### Linux
- **GNU Screen**

   Install using commands below
   
   ```
   apt-get install screen
   ```

#### OSX
No additional software required.

### Download
- **Option 1 (Standalone Jar):**

   Download the latest release from [our website](https://projects.vortexdata.net/tsq-plugin-framework). This ensures you are running a stable version and avoid bugs carried over from building stage.
   
- **Option 2 (Compile own version):**

   Clone the latest stable version of the source code and run it trough our compiler. It should output a file named "tsq-plugin-framework.jar".
   
### Installation

   Copy "tsq-plugin-framework.jar" to a folder you want the directory structure to get generated in. Then create a startup script. Here are some examples for different operating systems.
   
   #### Windows
   
   Create a file ending with '.bat', copy and paste the code below, then run the script.
   
   ```
   @echo off
   TITLE Teamspeak Query Plugin Framework
   java -jar tsq-plugin-framework.jar -debug
   PAUSE
   ```

   #### Linux
   
   Create a file ending with '.sh' (_touch launch.sh_), copy and paste the code below. If required, assign required permissions (chmod 755 launch.sh).
   
   ```
   java -jar tsq-plugin-framework.jar -debug
   ```
   
   Now, create a new screen session:
   
   ```
   screen -S session_name
   ```
   
   > ⚠️ **Important**: If you run the framework outside a screen session, it will terminate as soon as you disconnect from the terminal. Also, although it not really being an issue, it is recommended not to run the script as root user.
   
   Once you've created the session, it should automaticall be attached. Now run the launch script:
   
   ```
   ./launch.sh
   ```
   
   The framework will generate its directory structure and terminate after completion. Navigate to to the main properties file and adjust it to fit your servers prefferences. Once complete, run the script again (be sure you're runnning it using a screen session) and wait for the boot process to complete.
