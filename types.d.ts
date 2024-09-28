declare module '@dani.dev.pm/cordova-plugin-mlkit-doc-scanner' {

    /**
     * Options for scanning documents.
     */
    export interface ScanOptions {
        /**
         * The maximum number of pages to scan. (Default: no limit)
         */
        pageLimit?: number;
        
        /**
         * Whether to include JPEG images in the scan result. (Default: true)
         */
        includeJpeg?: boolean;
        
        /**
         * Whether to include a PDF in the scan result. (Default: true)
         */
        includePdf?: boolean;
    }

    /**
     * Result of a document scan.
     */
    export interface ScanResult {
        /**
         * Array of image URIs if JPEG format is included.
         */
        images?: string[];
        
        /**
         * URI of the PDF if PDF format is included.
         */
        pdf?: string;
    }

    /**
     * Interface for the ML Kit Document Scanner.
     */
    export interface MLKitDocScanner {
        /**
         * Scans a document with the given options.
         * @param options Options for scanning the document.
         * @returns A promise that resolves to the scan result.
         */
        scanDocument(options: ScanOptions): Promise<ScanResult>;
    }

    const MLKitDocScanner: MLKitDocScanner;
    export default MLKitDocScanner;
}