const express = require("express");
const {
  uploadOCRImage,
  processProductDetails,
  getAllProducts,
  getProductById,
  updateProductDetails,
} = require("../../controllers/gradingController");

const router = express.Router();

// Routes untuk OCR dan produk
router.post("/ocr", uploadOCRImage);
router.post("/process", processProductDetails);
router.get("/products", getAllProducts);
router.get("/products/:id", getProductById);
router.post("/products/:id/details", updateProductDetails);

module.exports = router;
