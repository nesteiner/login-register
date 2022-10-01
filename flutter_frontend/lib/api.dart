import 'dart:convert';

import 'package:crypto/crypto.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'models.dart';

const BASE_URL = "http://localhost:8082/api";

final options = BaseOptions(
  baseUrl: BASE_URL
);

final dio = Dio(options);

Future<String> login({required String username, required String password}) async {
  password = md5.convert(utf8.encode(password)).toString();

  Response response = await dio.post("/authenticate", data: {
    "username": username,
    "password": password
  });

  var jwttoken = response.data["jwttoken"];
  return "Bearer ${jwttoken}";
}

Future<User> findUser(String token) async {
  Response response = await dio.get("/user", options: Options(headers: {
    "Authorization": token
  }));

  return User.fromJson(response.data["data"]);
}

Future<User> changeAvatar(int avatarid, String token) async {
  final changeAvatarRequest = {"avatarid": avatarid};
  Response response = await dio.put("/user/avatar", data: changeAvatarRequest, options: Options(
    headers: {"Authorization": token}
  ));

  return User.fromJson(response.data["data"]);
}

Future<ImageItem> uploadImage(String path, String filename, String token) async {
  FormData formdata = FormData.fromMap(
      {"file": MultipartFile.fromFileSync(path, filename: filename)});

  Response response = await dio.post("/upload", data: formdata, options: Options(
    headers: {"Authorization": token}
  ));

  return ImageItem.fromJson(response.data["data"]);
}

void handleDioError(BuildContext context, DioError error) {
  showDialog(context: context, builder: (context) => AlertDialog(
    title: Text("Error occurs"),
    content: Text(error.response!.data["message"]),
    actions: [
      TextButton(
          onPressed: () {
            Navigator.of(context).pop();
          },
          child: Text("确定")
      )
    ],
  ));

  if(error.response!.statusCode == 401) {
    Navigator.of(context).popUntil((route) => route.isFirst);
  }
}

String fetchImageUrl(int avatarid) {
  return "${BASE_URL}/download/${avatarid}";
}