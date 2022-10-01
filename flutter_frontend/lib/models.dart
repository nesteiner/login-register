class ImageItem {
  int id;
  String name;

  ImageItem({required this.id, required this.name});

  static ImageItem fromJson(Map<String, dynamic> json) {
    return ImageItem(id: json["id"], name: json["name"]);
  }
}

class User {
  int id;
  String name;
  String email;
  ImageItem avatar;

  User({required this.id, required this.name, required this.email, required this.avatar});

  static User fromJson(Map<String, dynamic> json) {
    return User(id: json["id"], name: json["name"], email: json["email"], avatar: ImageItem.fromJson(json["avatar"]));
  }
}