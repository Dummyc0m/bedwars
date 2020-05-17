package bedwars

import com.dummyc0m.pylon.util.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

val iron = ItemStack(Material.IRON_INGOT)
val gold = ItemStack(Material.GOLD_INGOT)
val diamond = ItemStack(Material.DIAMOND)
val emerald = ItemStack(Material.EMERALD)

// TODO add this to config.yml
fun initBuyConfig(
        playerUpgrades: PlayerUpgrades,
        teamInfos: Hashtable<String, TeamInfo>
): BuyConfig =
        BuyConfig(
                playerUpgrades,
                teamInfos,
                woolCost = Resource(iron, 4),
                endStoneCost = Resource(iron, 24),

                chainmailArmorCost = Resource(iron, 40),
                ironArmorCost = Resource(gold, 12),
                diamondArmorCost = Resource(emerald, 6),

                woodPickCost = Resource(iron, 10),
                ironPickCost = Resource(iron, 10),
                goldPickCost = Resource(gold, 3),
                diamondPickCost = Resource(gold, 6),

                woodAxeCost = Resource(iron, 10),
                stoneAxeCost = Resource(iron, 10),
                ironAxeCost = Resource(gold, 3),
                diamondAxeCost = Resource(gold, 6),

                shearsCost = Resource(iron, 20),
                enderPearlCost = Resource(emerald, 4),
                tntCost = Resource(gold, 8),
                fireballCost = Resource(iron, 40),
                goldenAppleCost = Resource(gold, 3),
                waterBucketCost = Resource(gold, 6)
        )

data class Resource(
        val itemStack: ItemStack,
        val amount: Int
) {
    val total: ItemStack by lazy {
        val newStack = itemStack.clone()
        newStack.amount = amount
        newStack
    }

    override fun toString(): String {
        val name = if (itemStack.type == Material.GOLD_INGOT) {
            "${GOLD}$amount Gold"
        } else if (itemStack.type == Material.DIAMOND) {
            "${BLUE}$amount Diamond"
        } else if (itemStack.type == Material.EMERALD) {
            "${GREEN}$amount Emerald"
        } else if (itemStack.type == Material.IRON_INGOT) {
            "${GRAY}$amount Iron"
        } else {
            "${WHITE}$amount ${itemStack.type.name}"
        }
        return "$RESET$name"
    }
}

data class BuyConfig(
        val playerUpgrades: PlayerUpgrades,
        val teamInfos: Hashtable<String, TeamInfo>,

        val woolCost: Resource,
        val endStoneCost: Resource,

        val chainmailArmorCost: Resource,
        val ironArmorCost: Resource,
        val diamondArmorCost: Resource,

        val woodPickCost: Resource,
        val ironPickCost: Resource,
        val goldPickCost: Resource,
        val diamondPickCost: Resource,

        val woodAxeCost: Resource,
        val stoneAxeCost: Resource,
        val ironAxeCost: Resource,
        val diamondAxeCost: Resource,

        val shearsCost: Resource,

        val enderPearlCost: Resource,
        val tntCost: Resource,
        val fireballCost: Resource,
        val goldenAppleCost: Resource,
        val waterBucketCost: Resource
)