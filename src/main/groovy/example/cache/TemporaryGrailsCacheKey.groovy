package example.cache

import groovy.transform.CompileStatic

@CompileStatic
class TemporaryGrailsCacheKey implements Serializable {
    final String targetClassName
    final String targetMethodName
    final int targetObjectHashCode
    final Object simpleKey
    final String tenantId

    TemporaryGrailsCacheKey(String targetClassName, String targetMethodName,
                            int targetObjectHashCode, Object simpleKey,
                            Serializable tenantId) {
        this.targetClassName = targetClassName
        this.targetMethodName = targetMethodName
        this.targetObjectHashCode = targetObjectHashCode
        this.simpleKey = simpleKey
        this.tenantId = tenantId != null ? tenantId.toString() : null
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        TemporaryGrailsCacheKey that = (TemporaryGrailsCacheKey) o

        if (targetObjectHashCode != that.targetObjectHashCode) return false
        if (simpleKey != that.simpleKey) return false
        if (targetClassName != that.targetClassName) return false
        if (targetMethodName != that.targetMethodName) return false
        if (tenantId != that.tenantId) return false

        return true
    }

    int hashCode() {
        int result
        result = (targetClassName != null ? targetClassName.hashCode() : 0)
        result = 31 * result + (targetMethodName != null ? targetMethodName.hashCode() : 0)
        result = 31 * result + targetObjectHashCode
        result = 31 * result + (simpleKey != null ? simpleKey.hashCode() : 0)
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0)
        return result
    }
}
