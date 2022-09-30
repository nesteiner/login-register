<template>
  <nav>
    <div class="container">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <button @click="handleLogout">Logout</button>
  </nav>
  <router-view/>
</template>

<script lang="ts" setup>
import {logout} from "@/api";
import {useRoute, useRouter} from "vue-router";

const router = useRouter();
const route  = useRoute();
function handleLogout() {
  if(route.name != "home") {
    logout();
    router.replace({name: "login"})
  }
}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
  display: flex;
  justify-content: space-between;


  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
