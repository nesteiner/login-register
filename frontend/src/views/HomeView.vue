<template>
  <div class="home">
    <img alt="missing avatar" :src="imgsrc"/>
    <br/>
    <input type="file" ref="file">
    <button @click="handleSubmit">submit</button>
    <br/>
    <h1>{{user.name}}</h1>
    <h2>{{user.email}}</h2>
  </div>
</template>

<script lang="ts" setup>
import {onMounted, ref} from "vue";
import {findUser, fetchImageUrl, uploadImage, changeAvatar} from "@/api";
import {useRouter} from "vue-router";

const router = useRouter();
const image = ref<HTMLImageElement>()
const file = ref<HTMLInputElement>()
const imgsrc = ref("");
const user = ref<User>({
  id: 0,
  name: "loading",
  email: "loading",
  avatar: {
    id: 1,
    name: "default.png"
  }
});

async function handleSubmit() {
  if(file.value?.files?.length == 0) {
    alert("no file selected");
  }

  try {
    let image: ImageItem = await uploadImage(file.value?.files?.item(0)!);
    imgsrc.value = fetchImageUrl(image.id);
    await changeAvatar(image.id);
  } catch(error: any) {
    console.log(error)
    alert(error.data["message"])
    if (error.status == 401 || error.status == 400) {
      router.replace({name: "login"})
    }
  }

}

onMounted(async () => {
  try {
    user.value = await findUser();
    imgsrc.value = fetchImageUrl(user.value.avatar.id)
  } catch (error: any) {
    if(error.status == 401 || error.status == 400) {
      router.replace({name: "login"})
    }
  }
})
</script>
