## Jcloud XATTR bug

An exception occurs on file systems that don't support XATTR.
If you specify a Docker **mounted volume** in **Docker-for-mac** or **Docker-for-win** you will get an exception while reading a file. 

### Build

```
mvn clean package docker:build
```

### Run

```
docker run -it -v $PWD/data:/data jcloud-xattr-bug
```

### Exception


```
Exception in thread "main" java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.simontuffs.onejar.Boot.run(Boot.java:340)
	at com.simontuffs.onejar.Boot.main(Boot.java:166)
Caused by: java.lang.RuntimeException: java.nio.file.FileSystemException: /data/data-container/testFile: Unable to get list of extended attributes: Operation not supported
	at com.google.common.base.Throwables.propagate(Throwables.java:160)
	at org.jclouds.filesystem.strategy.internal.FilesystemStorageStrategyImpl.getBlob(FilesystemStorageStrategyImpl.java:394)
	at org.jclouds.blobstore.config.LocalBlobStore.loadBlob(LocalBlobStore.java:458)
	at org.jclouds.blobstore.config.LocalBlobStore.getBlob(LocalBlobStore.java:635)
	at org.jclouds.blobstore.config.LocalBlobStore.getBlob(LocalBlobStore.java:199)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at com.google.inject.internal.DelegatingInvocationHandler.invoke(DelegatingInvocationHandler.java:37)
	at com.sun.proxy.$Proxy38.getBlob(Unknown Source)
	at StartJcloudTest.start(StartJcloudTest.java:46)
	at StartJcloudTest.main(StartJcloudTest.java:18)
	... 6 more
Caused by: java.nio.file.FileSystemException: /data/data-container/testFile: Unable to get list of extended attributes: Operation not supported
	at sun.nio.fs.LinuxUserDefinedFileAttributeView.list(LinuxUserDefinedFileAttributeView.java:121)
	at org.jclouds.filesystem.strategy.internal.FilesystemStorageStrategyImpl.getBlob(FilesystemStorageStrategyImpl.java:347)
	... 17 more
```

### Fixed in

https://github.com/jclouds/jclouds/pull/1066 / https://issues.apache.org/jira/browse/JCLOUDS-1218
