public class FileNameExtractor{
    public String getName(String fullPath, String fileType) throws Exception{
        int fullPathLength = fullPath.length();
        int fileTypeLength = fileType.length();

        int startPoint = fullPathLength - fileTypeLength;
        int endPoint = fullPathLength - fileTypeLength;
        String checkType = fullPath.substring(endPoint,fullPathLength);

        if (startPoint > 0 && checkType.equals(fileType)) {
            while (fullPath.charAt(startPoint) != '\\' && startPoint > 0) {
                startPoint -= 1;
            }
            return fullPath.substring(startPoint+1, endPoint);
        } else {
            return "Wrong Path";
        }
    }
    public String getExtension(String fullPath) throws Exception{
        int fullPathLength = fullPath.length();
        if (fullPath.substring(fullPathLength-4,fullPathLength)=="jpeg"){
            return fullPath.substring(fullPathLength-4,fullPathLength);
        }else{
            return fullPath.substring(fullPathLength-3,fullPathLength);
        }

    }
}
