To compile:

1. Link "src/main/java" as a source folder
2. Link "src/main/resources" as a source folder
3. Add "server/craftbukkit-bin.jar" to the build path as a dependency
4. Add "server/java_websocket.jar" to the build path as a dependency
5. Set "watchdog.WatchDog" as the main class
6. Export the project as a runnable jar file

You cannot run the project from within the IDE, you must export it every time as a runnable jar file for both testing and production.