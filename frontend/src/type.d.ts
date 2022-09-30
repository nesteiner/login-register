declare interface RegisterRequest {
    username: string,
    passwordHash: string,
    email: string,
}

declare interface ImageItem {
    id: number,
    name: string
}

declare interface User {
    id: number,
    name: string,
    email: string,
    avatar: ImageItem
}

