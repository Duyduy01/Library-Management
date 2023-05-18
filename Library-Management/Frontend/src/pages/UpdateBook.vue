<template>
  <div class="bg">
    <the-navigation></the-navigation>
    <div class="header">
      <h1>Update book {{ information.title }}</h1>
      <div class="body-form">
        <form
          @submit.prevent="updateInformation"
          action=""
          class="form-add-book"
        >
          <div class="header-form">
            <label for="">
              Tiêu đề
              <input
                type="text"
                name="title"
                v-model.trim="information.title"
                required
              />
            </label>
            <label for=""
              >Tác giả
              <input
                type="text"
                name="author"
                v-model.trim="information.author"
                required
              />
            </label>
          </div>
          <div class="body-form-1">
            <label class="dis" for=""
              >Mô tả về sách
              <textarea
                type="text"
                name="discription"
                v-model.trim="information.discription"
              ></textarea>
            </label>
          </div>
          <div class="footer-form">
            <label for=""
              >Ngày phát hành
              <input
                type="text"
                name="date"
                v-model.trim="information.dateofissue"
                required
              />
            </label>
            <label for=""
              >Số trang
              <input
                type="text"
                name="pages"
                v-model.trim="information.pages"
                required
              />
            </label>
          </div>
          <label class="cate" for=""
            >Thể loại
            <input
              type="text"
              name="category"
              v-model.trim="information.category"
              required
            />
          </label>
          <div class="btnbox">
            <button type="submit" :disabled="!formIsValid" @click="uploadFile">
              Save
            </button>
          </div>
        </form>
        <div class="chooseImg">
          <button raised @click="onPickFile">UPLOAD IMG</button>
          <input
            type="file"
            ref="myfile"
            style="display: none"
            @change="previewFiles($event)"
          />
          <div class="img">
            <img alt="" :src="newImage" />
            <cloud-image
              v-show="toggle"
              class="img-1"
              :path="`images/img${this.$route.params.id}`"
            ></cloud-image>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { storage } from "../firebase";
import { ref, uploadBytes } from "firebase/storage";
import TheNavigation from "@/components/TheNavigation.vue";
import { mapMutations } from "vuex";
import { LOADING_SPINNER_SHOW_MUTATION } from "../store/storeconstants";
import CloudImage from "@/components/CloudImage.vue";

export default {
  name: "ViewBook",
  components: {
    TheNavigation,
    CloudImage,
  },
  data() {
    return {
      information: {
        title: "",
        author: "",
        discription: "",
        dateofissue: "",
        pages: "",
        category: "",
      },
      newImage: "",
      toggle: true,
      putStatus: "",
    };
  },
  computed: {
    formIsValid() {
      return (
        this.title !== "" &&
        this.author !== "" &&
        this.dateofissue !== "" &&
        this.pages !== "" &&
        this.category !== ""
      );
    },
  },
  async mounted() {
    const result = await axios.get(
      "http://localhost:6060/book/" + this.$route.params.id
    );
    console.log(result);
    this.information = result.data;

    // this.upload();
  },
  methods: {
    ...mapMutations({
      showLoading: LOADING_SPINNER_SHOW_MUTATION,
    }),
    async updateInformation() {
      if (!this.formIsValid) {
        alert("invalid");
      }
      const update = await axios.put(
        "http://localhost:6060/book/update/" + this.$route.params.id,
        {
          title: this.information.title,
          author: this.information.author,
          discription: this.information.discription,
          dateofissue: this.information.dateofissue,
          pages: this.information.pages,
          category: this.information.category,
        }
      );
      this.showLoading(true);
      this.putStatus = update.status;
      console.log(update);
    },
    async uploadFile() {
      const storageRef = ref(storage, `images/img${this.$route.params.id}`);
      const snapshot = await uploadBytes(
        storageRef,
        this.$refs.myfile.files[0]
      );
      console.log(snapshot);
      if (snapshot.metadata.name != null && this.putStatus == 200) {
        this.$router.push(`/view-book/${this.$route.params.id}`);
        this.showLoading(false);
      } else {
        alert("Somthing went wrong! Check your connection");
        return;
      }
    },
    previewFiles(event) {
      const file = event.target.files[0];

      const theReader = new FileReader();
      theReader.onloadend = async () => {
        this.newImage = await theReader.result;
      };
      theReader.readAsDataURL(file);
      this.toggle = false;
    },
    onPickFile() {
      this.$refs.myfile.click();
    },
  },
};
</script>

<style lang="css" scoped>
.bg {
  width: 100%;
  min-height: 100%;
  background: linear-gradient(to right, #80878d, #2c3e50);
}
.header {
  height: 90%;
  width: 100%;
  padding: 0 2rem;
}

.header h1 {
  position: relative;
  top: 10px;
}

.body-form {
  display: grid;
  place-items: center;
  width: 100%;
  height: 80%;
}

.form-add-book {
  background-color: #fff;
  border-radius: 10px;
  padding: 20px;
  width: 80%;
  /* height: 100%; */
  display: flex;
  flex-direction: column;
  margin: 1.5rem 2rem 0 2rem;
}

.form-add-book label {
  margin: 0 1.5rem 1rem 1.5rem;
}

.form-add-book button {
  display: block;
  margin: 1rem 1.5rem;
}

.header-form label {
  font-size: 1.1rem;
  display: inline-grid;
}

.header-form label input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  outline: 0;
  width: 100%;
  background-color: transparent;
  margin-top: 1vh;
}

.header-form label input:focus {
  border-color: #3366cc;
}

.body-form-1 label {
  width: 100%;
  padding-right: 50px;
  font-size: 1.1rem;
  display: inline-grid;
}

.body-form-1 textarea {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  outline: 0;
  width: 100%;
  background-color: transparent;
  resize: none;
  margin-top: 1vh;
}

.body-form-1 textarea:focus {
  border-color: #3366cc;
}

.body-form-1 textarea::-webkit-scrollbar {
  width: 1em;
}
.body-form-1 textarea::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
}

.body-form-1 textarea::-webkit-scrollbar-thumb {
  background-color: darkgrey;
  outline: 1px solid slategrey;
}
.footer-form label {
  display: inline-grid;
  font-size: 1.1rem;
}

.footer-form label input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  outline: 0;
  width: 100%;
  background-color: transparent;
  margin-top: 1vh;
}

.footer-form label input:focus {
  border-color: #3366cc;
}

.cate {
  display: inline-grid;
  font-size: 1.1rem;
}

.cate input {
  padding: 10px;
  border-radius: 5px;
  border: 1px solid rgba(0, 0, 0, 0.2);
  outline: 0;
  width: 100%;
  background-color: transparent;
  margin-top: 1vh;
}

.cate input:focus {
  border-color: #3366cc;
}
.btnbox {
  display: grid;
  place-items: center;
  width: 100%;
}

.btnbox button {
  padding: 1.3em 3em;
  margin: 0.5rem 1.5rem;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #000;
  background-color: #1875ff;
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;
}

.btnbox button:hover {
  background-color: #23c483;
  box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
  color: #fff;
  transform: translateY(-7px);
}

.chooseImg button:active {
  transform: translateY(-1px);
}

.chooseImg {
  display: grid;
  width: 80%;
  height: 500px;
  place-items: center;
  border-radius: 15px;
  background-color: #eadcca;
  border: 2px dashed #daa06d;
  box-shadow: 0 0 0 4px #eaddca, 2px 2px 4px 2px rgba(0, 0, 0, 0.5);
  padding: 20px 20px 50px 20px;
  grid-template-rows: 10% 90%;
  row-gap: 5%;
  margin: 3rem 2rem 2rem 2rem;
}

.chooseImg button {
  padding: 10px 35px;
  border-radius: 50px;
  border: 0;
  background-color: white;
  box-shadow: rgb(0 0 0 / 5%) 0 0 8px;
  letter-spacing: 1.5px;
  text-transform: uppercase;
  font-size: 15px;
  transition: all 0.5s ease;
}

.chooseImg button:hover {
  letter-spacing: 3px;
  background-color: hsl(261deg 80% 48%);
  color: hsl(0, 0%, 100%);
  box-shadow: rgb(93 24 220) 0px 7px 29px 0px;
}

.chooseImg button:active {
  letter-spacing: 3px;
  background-color: hsl(261deg 80% 48%);
  color: hsl(0, 0%, 100%);
  box-shadow: rgb(93 24 220) 0px 0px 0px 0px;
  transform: translateY(10px);
  transition: 100ms;
}

.chooseImg .img {
  display: flex;
  justify-content: center;
  align-items: center;
  max-width: 100%;
  object-fit: cover;
}

.img img {
  width: 100%;
  max-height: 400px;
}

.img-1 {
  width: 100%;
}
@media (min-width: 768px) {
  .body-form {
    grid-template-columns: repeat(2, 1fr);
  }
  .chooseImg {
    margin: 1.5rem 2rem 0 2rem;
  }
}
</style>
