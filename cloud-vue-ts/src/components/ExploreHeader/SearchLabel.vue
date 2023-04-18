<template>
  <div class="search-label">
    <div style="display: flex;justify-content: space-between;align-items: center;">
      <div><slot><CIcon name="user" /></slot> <span class="title">{{ resource.name }}</span></div>
      <div class="time">
        {{ new Date(resource.createTime).format("YYYY-MM-DD") }}
      </div>
    </div>
    <div>
      <span class="path">路径：{{ resource.path }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "SearchLabel",
  props: {
    resource: {
      type: Object,
      required: false,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {
      sizePx: this.size + "px"
    };
  },
  computed: {
  }
};
</script>

<style scoped lang="scss">
.search-label {
  width: 100%;
  > div {
    margin: 5px 0;
  }
  .title {
    font-size: 16px;
    font-weight: bold;
  }
  .time {
    font-size: 13px;
    color: #898989;
    padding-top: 3px;
  }
  .path {
    font-size: 13px;
    color: #898989;
  }
}
</style>
