import 'package:flutter/material.dart';
import 'package:flutter_frontend/api.dart' as api;
import 'package:flutter_frontend/models.dart';

class GlobalState extends ChangeNotifier {
  late String token;
  late User user;

  Future<void> setToken(String token) async {
    this.token = token;
    this.user = await api.findUser(token);
    notifyListeners();
  }

  Future<void> changeAvatar(int avatarid) async {
    user = await api.changeAvatar(avatarid, token);
    notifyListeners();
  }

  Future<ImageItem> uploadImage(String path, String filename) async {
    final imageitem = await api.uploadImage(path, filename, token);
    user.avatar = imageitem;
    notifyListeners();
    return imageitem;
  }
}