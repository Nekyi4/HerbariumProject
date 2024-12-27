import org.apache.commons.imaging.ImageFormats
import org.apache.commons.imaging.Imaging
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.ByteArrayInputStream

class imageHandler {

    fun imageArrToByteArray(imageArr: Array<Array<IntArray>>): ByteArray {
        val byteList = mutableListOf<Byte>()

        for (y in imageArr.indices) {
            for (x in imageArr[y].indices) {
                val r = imageArr[y][x][0].toByte()
                val g = imageArr[y][x][1].toByte()
                val b = imageArr[y][x][2].toByte()

                // Add RGB values to byte list
                byteList.add(r)
                byteList.add(g)
                byteList.add(b)
            }
        }

        // Convert list to ByteArray
        return byteList.toByteArray()
    }

    fun imagineLoader(param: String): ByteArray {
        val img = Imaging.getBufferedImage(File(param))
        val baos = ByteArrayOutputStream()
        Imaging.writeImage(img, baos, ImageFormats.PNG)
        return baos.toByteArray()
    }

    fun saveImage(imageData: ByteArray, outputPath: String) {
        val img = Imaging.getBufferedImage(ByteArrayInputStream(imageData))
        val outputFile = File(outputPath)
        Imaging.writeImage(img, outputFile, ImageFormats.PNG)
        println("Image saved at $outputPath")
    }

    // Encoding function: converts image pixel array to a string with width and height prepended
    fun encodeImage(imageMatrix: Array<Array<IntArray>>): String {
        val sb = StringBuilder()

        // Get the width and height of the image
        val width = imageMatrix[0].size
        val height = imageMatrix.size

        // Prepend width and height to the encoded string
        sb.append("$width,$height,")

        // Iterate over each pixel and encode its RGB values
        for (y in imageMatrix.indices) {
            for (x in imageMatrix[y].indices) {
                val r = imageMatrix[y][x][0]
                val g = imageMatrix[y][x][1]
                val b = imageMatrix[y][x][2]

                // Append RGB values to the StringBuilder, separated by "x"
                sb.append("$r" + "x" + "$g" + "x" + "$b")

                // Add a separator between pixels (not at the end of the string)
                if (x != imageMatrix[y].lastIndex || y != imageMatrix.lastIndex) {
                    sb.append(",")
                }
            }
        }

        return sb.toString()
    }

    // Decoding function: converts an encoded string back into a 2D image array
    fun decodeImage(encodedString: String): Array<Array<IntArray>> {
        // Split the string by commas to get the width, height, and pixel data
        val parts = encodedString.split(",")

        // Extract the width and height from the first two elements
        val width = parts[0].toInt()
        val height = parts[1].toInt()

        // Create a 2D array to hold the image pixel data
        val imageMatrix = Array(height) { Array(width) { IntArray(3) } }

        // Process the remaining parts as pixel data
        var index = 2 // Start after width and height
        for (y in 0 until height) {
            for (x in 0 until width) {
                // Get the encoded pixel values (e.g., "255x0x0" for a red pixel)
                val pixelValues = parts[index].split("x")
                val r = pixelValues[0].toInt()
                val g = pixelValues[1].toInt()
                val b = pixelValues[2].toInt()

                // Assign the decoded values to the image matrix
                imageMatrix[y][x] = intArrayOf(r, g, b)

                // Move to the next pixel
                index++
            }
        }

        return imageMatrix
    }
}