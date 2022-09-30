<template>
  <div class="register-view">
    <input type="text" v-model="username" placeholder="input username"/>
    <br/>
    <input type="email" v-model="email" placeholder="input email"/>
    <br/>
    <input type="password" v-model="password" placeholder="input password"/>
    <br/>
    <button @click="handleRegister">Register</button>
  </div>
</template>

<script lang="ts" setup>
import {ref} from "vue";
import {useRouter} from "vue-router";
import {register} from "@/api";

const username = ref("")
const email = ref("")
const password = ref("")

const router = useRouter();
async function handleRegister() {
  try {
    let request: RegisterRequest = {
      username: username.value,
      passwordHash: password.value,
      email: email.value
    };

    await register(request);
    router.replace({name: "login"});
  } catch (error: any) {
    console.log(error.response)
    alert(error.response.data["message"]);
  } finally {
    email.value = "";
    password.value = "";
  }
}
</script>

<style scoped>

</style>