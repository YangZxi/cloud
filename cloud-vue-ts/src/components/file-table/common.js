import { h } from "vue";
import CIcon from "@/components/mie-ui/CIcon";

const types = {
  image: {
    suffix: ["jpg", "png", "jpeg"],
    icon: "file-image"
  },
  video: {
    suffix: ["mp4", "avi"],
    icon: "file-video"
  },
  audio: {
    suffix: ["mp3", "ogg"],
    icon: "file-audio"
  },
  text: {
    suffix: ["txt", "js", "ts", "java", "md"],
    icon: "file-code"
  },
  package: {
    suffix: ["zip", "rar", "7z", "tar"],
    icon: "file-zipper"
  }
};

export const fileIconName = function(type = "FILE") {
  let icon = "file";
  type = type.toLowerCase();
  if (type === "dir") return "fa-folder";
  for (let el of Object.values(types)) {
    if (el.suffix.includes(type)) {
      icon = el.icon;
    }
  }
  return "fa-" + icon;
};

export const fileIcon = function(type = "file", size = 13) {
  return h(
    CIcon,
    {
      name: fileIconName(type),
      size,
      color: type === "dir" ? "#E66E05" : "#333",
      class: "fileIcon"
    },
    null
  );
};
