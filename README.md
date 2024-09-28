# Cordova Plugin: ML Kit Document Scanner

An Android plugin that implements Google's ML Kit Document Scanner in a Cordova/Ionic app.

Check [here](https://developers.google.com/ml-kit/vision/doc-scanner) the documentation for MLKit document scanner.


## Installation

To install the plugin in Ionic:

```bash
ionic cordova plugin add @dani.dev.pm/cordova-plugin-mlkit-doc-scanner
```

## Usage

```typescript
import MLKitDocScanner from '@dani.dev.pm/cordova-plugin-mlkit-doc-scanner';
MLKitDocScanner.scanDocument(options)
.then((result) => {
// Handle the scan result
})
.catch((error) => {
// Handle any errors
});

// OR

const result: ScanResult = await MLKitDocScanner.scanDocument(options);
```

## API Reference

### MLKitDocScanner

The main interface for the ML Kit Document Scanner.

#### Methods

##### `scanDocument(options: ScanOptions): Promise<ScanResult>`

Scans a document with the given options.

- `options`: Options for scanning the document (see `ScanOptions` interface).
- Returns: A promise that resolves to the scan result (see `ScanResult` interface).

### Interfaces

#### ScanOptions

Options for scanning documents.

| Property | Type | Description | Default |
|----------|------|-------------|---------|
| `pageLimit?` | `number` | The maximum number of pages to scan. | No limit |
| `includeJpeg?` | `boolean` | Whether to include JPEG images in the scan result. | `true` |
| `includePdf?` | `boolean` | Whether to include a PDF in the scan result. | `true` |

#### ScanResult

Result of a document scan.

| Property | Type | Description |
|----------|------|-------------|
| `images?` | `string[]` | Array of image URIs if JPEG format is included. |
| `pdf?` | `string` | URI of the PDF if PDF format is included. |

## Example

```typescript
import MLKitDocScanner from '@dani.dev.pm/cordova-plugin-mlkit-doc-scanner';
const scanOptions: ScanOptions = {
    pageLimit: 5,
    includeJpeg: true,
    includePdf: true
};

const { images, pdf } = await MLKitDocScanner.scanDocument(scanOptions);
console.log('Scanned jpeg of each page URIs:', images);
console.log('Scanned PDF uri:', pdf);
```
