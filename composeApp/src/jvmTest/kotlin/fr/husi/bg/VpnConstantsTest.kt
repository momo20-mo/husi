package fr.husi.bg

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class VpnConstantsTest {

    @Test
    fun `private vlan router address is one greater than client address`() {
        assertEquals("172.19.0.2", VpnConstants.privateVlan4Router(VpnConstants.PRIVATE_VLAN4_CLIENT))
        assertEquals("1.0.0.2", VpnConstants.privateVlan4Router("1.0.0.1"))
        assertEquals("1.0.1.0", VpnConstants.privateVlan4Router("1.0.0.255"))
    }

    @Test
    fun `private vlan client address must have a next address`() {
        assertTrue(VpnConstants.isValidPrivateVlan4Client("1.0.0.1"))
        assertFalse(VpnConstants.isValidPrivateVlan4Client("255.255.255.255"))
        assertFalse(VpnConstants.isValidPrivateVlan4Client("invalid"))
    }
}
