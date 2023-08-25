// Rather than scattering important data definitions that may need to be tweaked around the code,
// define them here.

package us.lyjia.NiceYtDlpGui;

import java.util.LinkedHashMap;
import java.util.Map;

public class Tweakables {
  
  public static Map<String, String> initDownloadProgressTemplateMap() {
    // key order is important
    LinkedHashMap<String, String> downloadProgressTemplateMap = new LinkedHashMap<>();
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_STATUS, "%(progress.status)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_EXTRACTOR, "%(info.extractor)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_DOWNLOADED, "%(progress.downloaded_bytes)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_TOTAL, "%(progress.total_bytes)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_ETA, "%(progress.eta)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_SPEED, "%(progress.speed)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_ID, "%(info.id)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_DOMAIN, "%(info.webpage_url_domain)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_TITLE, "%(info.title)s");
    return downloadProgressTemplateMap;
  }
}
