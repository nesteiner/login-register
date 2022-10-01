import 'package:flutter/material.dart';

class FileUploadButton extends StatelessWidget {
  void Function() onPressed;

  FileUploadButton({required this.onPressed});
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return ElevatedButton(
        onPressed: onPressed,
        child: Text("Upload File")
    );
  }
}