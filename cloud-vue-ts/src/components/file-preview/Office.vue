<template>
  <div>
    <vue-office-docx 
      :src="props.url"
      style="height: 100vh;"
      @rendered="rendered"
    />
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import VueOfficeDocx from "@vue-office/docx";
//引入相关样式
import "@vue-office/docx/lib/index.css";

const props = defineProps({
  resource: Object,
  url: String
});

// function renderAsync() {}

const resource = ref(props.resource);

// watch(() => props.resource, (val) => {
//   resource.value = props.resource;
//   console.log("ref");
//   refreshCode();
// });

onMounted(() => {
});


const rendered = function() {
  console.log("ok");
}

</script>

<style scoped>
.code-preview {
  width: 100%;
  height: 100%;
}
</style>
