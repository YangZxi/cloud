<template>
  <vue-office-docx
    :src="props.url"
    @rendered="rendered"
  />
</template>

<script setup>
// import { ref } from "vue";
import VueOfficeDocx from "@vue-office/docx";
// 引入相关样式
import "@vue-office/docx/lib/index.css";

const props = defineProps({
  resource: Object,
  url: String
});

// function renderAsync() {}

// watch(() => props.resource, (val) => {
//   resource.value = props.resource;
//   console.log("ref");
//   refreshCode();
// });

const rendered = function() {
  console.log("ok");
};

</script>

<style scoped>

</style>
