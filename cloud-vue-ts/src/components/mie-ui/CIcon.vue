<template>
  <span class="cloud-icon">
    <slot>
      <i
        :class="getIconClass"
        :style="{color: color, fontSize: sizePx}"
      />
    </slot>
  </span>
</template>

<script lang="ts">
export default {
  name: "CIcon",
  props: {
    name: {
      type: String,
      required: false,
      default: ""
    },
    size: {
      type: [Number, String],
      default: 16
    },
    color: {
      type: String,
      default: undefined
    }
  },
  data() {
    return {
      sizePx: (typeof this.size === 'number' ? this.size : parseInt(this.size)) + "px"
    };
  },
  computed: {
    getIconClass() {
      return this.name.startsWith("fa-") ? `fa ${this.name}` : this.name;
    }
  }
};
</script>

<style>
.cloud-icon {
  display: inline-flex;
  align-items: center;
  text-align: center;
  padding: 0 3px;
}
</style>
