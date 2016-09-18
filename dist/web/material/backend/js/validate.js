/**
 * Created by sunshine on 16/9/7.
 */
function not_null(item) {
    if (item == null || item == "" || item.length <= 0) {
        return false;
    }
    return true;
}