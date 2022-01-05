package example.cache

@SuppressWarnings("serial")
class CacheKey implements Serializable {
    final String targetClassName
    final String targetMethodName
    final int targetObjectHashCode
    final Object simpleKey

    CacheKey(String targetClassName, String targetMethodName,
             int targetObjectHashCode, Object simpleKey) {
        this.targetClassName = targetClassName
        this.targetMethodName = targetMethodName
        this.targetObjectHashCode = targetObjectHashCode
        this.simpleKey = simpleKey
    }
    @Override
    int hashCode() {
        final int prime = 31
        int result = 1
        result = prime * result
        + ((simpleKey == null) ? 0 : simpleKey.hashCode())
        result = prime * result
        + ((targetClassName == null) ? 0 : targetClassName
                .hashCode())
        result = prime * result
        + ((targetMethodName == null) ? 0 : targetMethodName
                .hashCode())
        result = prime * result + targetObjectHashCode
        return result
    }
    @Override
    boolean equals(Object obj) {
        if (this.is(obj))
            return true
        if (obj == null)
            return false
        if (getClass() != obj.getClass())
            return false
        CacheKey other = (CacheKey) obj
        if (simpleKey == null) {
            if (other.simpleKey != null)
                return false
        } else if (!simpleKey.equals(other.simpleKey))
            return false
        else if ( simpleKey.equals(other.simpleKey) && !(simpleKey instanceof Map && ((Map)simpleKey).size() == 0 ) ) {
            return true // equal if simpleKey is identical but not an empty map
        }

        if (targetClassName == null) {
            if (other.targetClassName != null)
                return false
        } else if (!targetClassName.equals(other.targetClassName))
            return false
        if (targetMethodName == null) {
            if (other.targetMethodName != null)
                return false
        } else if (!targetMethodName.equals(other.targetMethodName))
            return false
        if (targetObjectHashCode != other.targetObjectHashCode)
            return false
        return true
    }
}
