<template>
  <img class="img" :src="url" alt="" />
</template>

<script>
import { storage } from "../firebase";
import { ref, getDownloadURL } from "firebase/storage";
export default {
  props: {
    path: [String, Number],
  },
  data: () => {
    return {
      url: "https://via.placeholder.com/140x100",
    };
  },
  async mounted() {
    try {
      const download_url = await getDownloadURL(ref(storage, this.path));
      this.url = download_url;
      console.log(this.path);
    } catch (error) {
      console.error(error);
    }
  },
};
</script>

<style scoped lang="css">
.img {
  object-fit: cover;
  image-rendering: pixelated;
  height: 80%;
  width: 80%;
  display: block;
  filter: drop-shadow(0 0 5px blue);
}
</style>
