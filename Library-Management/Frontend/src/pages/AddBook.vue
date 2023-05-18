<template>
  <div class="bg">
    <the-navigation></the-navigation>
    <div class="header">
      <h1>Add new book</h1>
      <div class="body-form">
        <form @submit.prevent="addBook" action="" class="form-add-book">
          <div class="header-form">
            <label for=""
              >Tiêu đề
              <input
                type="text"
                name="title"
                v-model="courses.title"
                required
              />
            </label>
            <label for=""
              >Tác giả
              <input
                type="text"
                name="author"
                v-model="courses.author"
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
                v-model="courses.discription"
              ></textarea>
            </label>
          </div>
          <div class="footer-form">
            <label for=""
              >Ngày phát hành
              <input
                type="text"
                name="date"
                v-model="courses.dateofissue"
                required
              />
            </label>
            <label for=""
              >Số trang
              <input
                type="text"
                name="pages"
                v-model="courses.pages"
                required
              />
            </label>
          </div>
          <label class="cate" for=""
            >Thể loại
            <input
              type="text"
              name="category"
              v-model="courses.category"
              required
            />
          </label>
          <div class="btnbox">
            <button type="submit" :disabled="!formIsValid" @click="uploadFile">
              <div class="svg-wrapper-1">
                <div class="svg-wrapper">
                  <svg
                    height="24"
                    width="24"
                    viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path d="M0 0h24v24H0z" fill="none"></path>
                    <path
                      d="M1.946 9.315c-.522-.174-.527-.455.01-.634l19.087-6.362c.529-.176.832.12.684.638l-5.454 19.086c-.15.529-.455.547-.679.045L12 14l6-8-8 6-8.054-2.685z"
                      fill="currentColor"
                    ></path>
                  </svg>
                </div>
              </div>
              <span>Add book</span>
            </button>
          </div>
        </form>
        <div class="chooseImg">
          <button raised @click="onPickFile">Upload IMG</button>
          <input
            type="file"
            ref="myfile"
            style="display: none"
            @change="previewFiles($event)"
          />
          <div class="img">
            <img
              alt=""
              :src="
                newImage ||
                'https://www.namepros.com/attachments/empty-png.89209/'
              "
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TheNavigation from "@/components/TheNavigation.vue";
import { storage } from "../firebase";
import { ref, uploadBytes } from "firebase/storage";

export default {
  name: "AddBook",
  components: { TheNavigation },
  data() {
    return {
      courses: {
        title: "",
        author: "",
        discription: "",
        dateofissue: "",
        pages: "",
        category: "",
      },
      newImage: "",
      imgID: "",
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
  methods: {
    async addBook() {
      if (!this.formIsValid) {
        return;
      }
      const result = await axios.post(`http://localhost:6060/book/save`, {
        title: this.courses.title,
        author: this.courses.author,
        discription: this.courses.discription,
        dateofissue: this.courses.dateofissue,
        pages: this.courses.pages,
        category: this.courses.category,
        imageUrl: this.courses.imageUrl,
      });
      console.log(result.data);
      // this.$store.dispatch(Create_Book, result)
      if (result.status == 200) {
        console.log(result.data.id);
        this.imgID = result.data.id;
        this.uploadFile();
        this.$router.push("/posts");
      } else {
        alert("Somthing went wrong! Check your connection");
      }
    },
    async uploadFile() {
      const storageRef = ref(storage, `images/img${this.imgID}`);
      const snapshot = await uploadBytes(
        storageRef,
        this.$refs.myfile.files[0]
      );
      console.log(snapshot.metadata.name);
    },
    previewFiles(event) {
      const file = event.target.files[0];

      const theReader = new FileReader();
      theReader.onloadend = async () => {
        this.newImage = await theReader.result;
      };
      theReader.readAsDataURL(file);
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
  font-family: inherit;
  font-size: 20px;
  background: royalblue;
  color: white;
  padding: 0.7em 1em;
  padding-left: 0.9em;
  margin: 0.2rem 1.5rem;
  display: flex;
  align-items: center;
  border: none;
  border-radius: 16px;
  overflow: hidden;
  transition: all 0.2s;
}

.btnbox button span {
  display: block;
  margin-left: 0.3em;
  transition: all 0.3s ease-in-out;
}

.btnbox button:hover {
  background-color: #27408b;
}

.btnbox button svg {
  display: block;
  transform-origin: center center;
  transition: transform 0.3s ease-in-out;
}

.btnbox button:hover .svg-wrapper {
  animation: fly-1 0.6s ease-in-out infinite alternate;
}

.btnbox button:hover svg {
  transform: translateX(2.4em) rotate(45deg) scale(1.1);
}

.btnbox button:hover span {
  transform: translateX(10em);
}

.btnbox button:active {
  transform: scale(0.95);
}

@keyframes fly-1 {
  from {
    transform: translateY(0.1em);
  }

  to {
    transform: translateY(-0.1em);
  }
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
  width: 100%;
  object-fit: cover;
}

.img img {
  max-width: 100%;
  max-height: 400px;
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
