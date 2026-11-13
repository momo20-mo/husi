package fr.husi.bg

object VpnConstants {
    const val PRIVATE_VLAN4_CLIENT = "172.19.0.1"
    const val PRIVATE_VLAN6_CLIENT = "fdfe:dcba:9876::1"
    const val PRIVATE_VLAN6_ROUTER = "fdfe:dcba:9876::2"

    fun privateVlan4Router(clientAddress: String): String {
        val address = parseIpv4(clientAddress)
            ?.takeIf { it < MAX_IPV4 }
            ?: throw IllegalArgumentException("Invalid VPN interface IPv4 address: $clientAddress")
        val routerAddress = address + 1
        return (3 downTo 0).joinToString(".") { shift ->
            ((routerAddress shr (shift * 8)) and 0xFF).toString()
        }
    }

    fun isValidPrivateVlan4Client(clientAddress: String): Boolean =
        parseIpv4(clientAddress)?.let { it < MAX_IPV4 } == true

    private fun parseIpv4(address: String): Long? {
        val octets = address.split('.')
        if (octets.size != 4) return null

        var result = 0L
        for (octet in octets) {
            val value = octet.toIntOrNull()?.takeIf { it in 0..255 } ?: return null
            result = (result shl 8) or value.toLong()
        }
        return result
    }

    private const val MAX_IPV4 = 0xFFFF_FFFFL
}
