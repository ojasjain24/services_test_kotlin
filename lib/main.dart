import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MaterialApp(title: "Neon Widgets", home: MyApp()));
}

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }
}

class MyAppState extends State<MyApp> {
  void startServiceInPlatform() async {
    if (Platform.isAndroid) {
      const methodChannel = MethodChannel("com.smart.aeglepro_client.upload");
      await methodChannel.invokeMethod("pickFile").then((value) => print("CALLED FROM FLUTTER"));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: InkWell(
          onTap: startServiceInPlatform,
          child: Container(
            child: const Text("TAP HERE"),
            color: Colors.green,
          ),
        ),
      ),
    );
  }
}
