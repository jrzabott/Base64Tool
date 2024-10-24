
# Base64Tool

Base64Tool is a simple utility for converting files to Base64 encoding, with the option to compress the file before encoding. This can be useful for transmitting data over networks or in any situation where the content needs to be handled as text.

## Features

- Convert files to Base64.
- Optionally compress the file using LZMA2 (XZ) before encoding to Base64.
- Decode Base64 content and optionally decompress the result.
- Useful for converting binary files into a transmittable text format.

## Prerequisites

- Java 8 or higher
- LZMA2/XZ support for optional compression

## Usage

### Encoding

To encode a file to Base64:

```bash
java -jar Base64FileTool.jar encode <input_file_path> <output_base64_file>
```

#### Example:
```bash
java -jar Base64FileTool.jar encode sample.png encoded.txt
```

### Encoding with Compression

To encode a file and compress it before encoding to Base64:

```bash
java -jar Base64FileTool.jar encode <input_file_path> <output_base64_file> --compress
```

#### Example:
```bash
java -jar Base64FileTool.jar encode sample.png encoded.txt --compress
```

### Decoding

To decode a Base64-encoded file back into its original format:

```bash
java -jar Base64FileTool.jar decode <input_base64_file> <output_file_path>
```

#### Example:
```bash
java -jar Base64FileTool.jar decode encoded.txt decoded.png
```

### Decoding with Decompression

To decode a Base64-encoded and compressed file back into its original format:

```bash
java -jar Base64FileTool.jar decode <input_base64_file> <output_file_path> --compress
```

#### Example:
```bash
java -jar Base64FileTool.jar decode encoded.txt decoded.png --compress
```

## How It Works

1. **Encoding**: The tool reads the input file, optionally compresses the file using LZMA2/XZ, then encodes the result as Base64 and writes it to the specified output file.
2. **Decoding**: The tool reads a Base64-encoded file, decodes it back to binary, optionally decompresses it (if compression was used during encoding), and writes the output to the specified file.

## License

This project is open-source and available under the MIT License.
