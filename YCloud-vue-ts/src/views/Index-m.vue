<template>
  <div>
    <router-view
      style="padding-bottom: calc(var(--bottom-navigation-height) + 15px);height: 100%;box-sizing: border-box;
        background-color: #FFF;"
    />
    <var-bottom-navigation
      v-model:active="active"
      fixed
      safe-area
      class="navigation-m"
      @change="changeNav"
    >
      <template #fab>
        <!-- <var-uploader
          v-model="files"
          hide-list
          @after-read="upload"
        >
          <var-icon name="upload" />
        </var-uploader> -->
        <YUploader
          :upload-data="{
            'bucketName': bucketName,
            'path': path
          }"
          :upload-success="refresh"
          hide-list
        >
          <var-icon
            name="upload"
            color="#FFF"
          />
        </YUploader>
      </template>
      <var-bottom-navigation-item
        name="Home"
        label="首页"
        icon="home"
      />
      <var-bottom-navigation-item
        name="Explorer"
        label="文件"
      >
        <template #icon>
          <y-icon name="fa-folder" />
        </template>
      </var-bottom-navigation-item>
      <var-bottom-navigation-item
        name="MineShare"
        label="分享"
      >
        <template #icon>
          <y-icon name="fa-share-alt-square" />
        </template>
      </var-bottom-navigation-item>
      <var-bottom-navigation-item
        name="Mine"
        label="我的"
        icon="account-circle"
      />
    </var-bottom-navigation>
  </div>
</template>

<script setup lang="ts">
import { provide, ref } from "vue";
import { useRoute, useRouter } from "vue-router";

const $route = useRoute();
const $router = useRouter();

const bucketName = ref($route.params.name as string);
const path = ref("");

const active = ref<number | string>($route.name as string);

const refresh = ref<Function>(() => {
  console.log("refresh");
});

const changeNav = function(active: number | string) {
  if (typeof active === "string") {
    $router.push({ name: active });
  }
};

provide("setRefresh", (fn: Function) => {
  refresh.value = fn;
});
provide("setUploadPath", (val: string) => {
  path.value = val;
});
</script>

<style lang="scss">
</style>
