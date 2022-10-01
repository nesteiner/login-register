import Axios from "axios";
import {Md5} from "ts-md5";
const BASE_URL = "http://localhost/api"

const instance = Axios.create({
    baseURL: BASE_URL
});

const LOCAL_TOKEN_KEY = "token";
async function login(username: string, password: string) {
    password = Md5.hashStr(password);
    let jwttoken = await instance.post("/authenticate", {
        username,
        password
    }).then(response => response.data["jwttoken"])
        .catch(error => {
            throw error.response.data["message"]
        });

    let authorization = `Bearer ${jwttoken}`;
    localStorage.setItem(LOCAL_TOKEN_KEY, authorization);
}

function logout() {
    localStorage.removeItem(LOCAL_TOKEN_KEY);
}

async function register(request: RegisterRequest) {
    let password = request.passwordHash;
    request.passwordHash = Md5.hashStr(password);
    let response = await instance.post("/register", request);
    return response.data["data"];
}

async function findUser() {
    let token = localStorage.getItem(LOCAL_TOKEN_KEY) || "no token";
    let response = await instance.get("/user", {headers: {"Authorization": token}})
        .catch(error => {
            throw error.response;
        });
    return response.data["data"];
}

async function changeAvatar(avatarid: number) {
    let changeAvatarRequest = {
        avatarid
    }
    let jwttoken = localStorage.getItem(LOCAL_TOKEN_KEY) || "no token";

    let response = await instance.put("/user/avatar", changeAvatarRequest, {headers: {"Authorization": jwttoken}})
        .catch(error => {
            throw error.response
        });

    return response.data["data"]
}

async function uploadImage(image: File) {
    let formdata = new FormData();
    formdata.append("file", image);
    let jwttoken = localStorage.getItem(LOCAL_TOKEN_KEY) || "no token";
    let response = await instance.post("/upload", formdata, {headers: {"Authorization": jwttoken}})
        .catch(error => {
            throw error.response;
        });
    return response.data["data"];
}


async function deleteImage(id: number) {
    let jwttoken = localStorage.getItem(LOCAL_TOKEN_KEY) || "no token";
    let response = await instance.delete(`/images/${id}`, {headers: {"Authorization": jwttoken}})
        .catch(error => {
            throw error.response
        });
    return response.data["data"];
}

function fetchImageUrl(id: number) {
    return `${BASE_URL}/download/${id}`
}

export {
    login,
    logout,
    register,
    findUser,
    uploadImage,
    deleteImage,
    fetchImageUrl,
    changeAvatar
}