import 'package:dio/dio.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_frontend/api.dart';
import 'package:flutter_frontend/models.dart';
import 'package:flutter_frontend/state.dart';
import 'package:flutter_frontend/widgets.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatelessWidget {
  TextEditingController usernameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Login"),),
      body: Consumer(
        builder: (context, GlobalState state, child) {
          return buildBody(context, state);
        },
      ),
    );
  }

  Widget buildBody(BuildContext context, GlobalState state) {
    final headIcon = Container(
      padding: const EdgeInsets.all(60),
      child: const Icon(Icons.login, size: 100,),
    );

    final usernameInput = Container(
      padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 5),
      child: TextField(
        controller: usernameController,
        decoration: const InputDecoration(
          border: OutlineInputBorder(),
          labelText: "Username",
          hintText: "Enter username"
        ),
      ),
    );

    final passwordInput = Container(
      padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 5),
      child: TextField(
        controller: passwordController,
        obscureText: true,
        decoration: const InputDecoration(
          border: OutlineInputBorder(),
          labelText: "Password",
          hintText: "Enter password"
        ),
      ),
    );

    final loginButton = Container(
      height: 50,
      width: 250,
      decoration: BoxDecoration(
        color: Colors.blue,
        borderRadius: BorderRadius.circular(20)
      ),

      child: ElevatedButton(
        onPressed: () async {
          try {
            final token = await login(username: usernameController.text, password: passwordController.text);
            await state.setToken(token);
            Navigator.of(context).pushReplacement(
                MaterialPageRoute(builder: (_) => HomePage())
            );
          } on DioError catch(error) {
            handleDioError(context, error);
          } finally {
            usernameController.text = "";
            passwordController.text = "";
          }
        },

        child: const Text("Login", style: TextStyle(color: Colors.white),),
      ),
    );

    return Column(
      children: [
        headIcon,
        usernameInput,
        passwordInput,
        loginButton
      ],
    );
  }
}

class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("HomePage"),
        actions: [
          TextButton(
              onPressed: () {
                Navigator.of(context).pushReplacement(MaterialPageRoute(builder: (_) => LoginPage()));
              },
              child: Text("Logout", style: TextStyle(color: Colors.white),)
          )
        ],
      ),

      body: Consumer(builder: (context, GlobalState state, child) {
        return Center(
          child: buildBody(context, state),
        );
      }),
    );
  }

  Widget buildBody(BuildContext context, GlobalState state) {
    final User user = state.user;
    final image = Image.network(fetchImageUrl(user.avatar.id), height: 50, width: 50,);
    final fileupload = FileUploadButton(onPressed: () async {
      final picked = await FilePicker.platform.pickFiles();
      if(picked != null) {
        try {
          String path = picked.paths[0]!;
          String filename = picked.names[0]!;
          final imageitem = await state.uploadImage(path, filename);
          await state.changeAvatar(imageitem.id);
        } on DioError catch(error) {
          handleDioError(context, error);
        }
      }
    });

    final username = Text(user.name);
    final email = Text(user.email);

    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        image,
        fileupload,
        username,
        email
      ],
    );
  }
}