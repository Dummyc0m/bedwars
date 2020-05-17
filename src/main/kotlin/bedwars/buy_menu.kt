package bedwars

import com.dummyc0m.pylon.app.component.*
import com.dummyc0m.pylon.app.view.*
import com.dummyc0m.pylon.util.RESET
import com.dummyc0m.pylon.util.itemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.plugin.java.JavaPlugin

fun launchBuyMenu(
        javaPlugin: JavaPlugin,
        buyConfig: BuyConfig,
        player: Player
) {
    bootstrapApp(javaPlugin, player) {
        BuyNav(it, buyConfig, player)
    }
}

sealed class BuyRoute

private object HomeRoute : BuyRoute()
private object BlockRoute : BuyRoute()
private object ToolRoute : BuyRoute()
private object ArmorRoute : BuyRoute()
private object UtilRoute : BuyRoute()

class NavBar(app: AppRoot, private val routeCallback: (BuyRoute) -> Unit) : Component(app) {
    fun render(currentRoute: BuyRoute): ViewElement {
        val enchantRoute: ItemElement.(BuyRoute) -> Unit = {
            if (currentRoute == it) {
                enchant(Enchantment.SILK_TOUCH, 1)
                flag(ItemFlag.HIDE_ENCHANTS)
            } else {
                onClick { _, _ -> routeCallback(it) }
            }
        }
        return container {
            i(0, 0) {
                material = Material.NETHER_STAR
                displayName = "${RESET}Home"
                enchantRoute(HomeRoute)
            }
            i(1, 0) {
                material = Material.ENDER_STONE
                displayName = "${RESET}Blocks"
                enchantRoute(BlockRoute)
            }
            i(2, 0) {
                material = Material.DIAMOND_PICKAXE
                displayName = "${RESET}Tools"
                enchantRoute(ToolRoute)
            }
            i(3, 0) {
                material = Material.DIAMOND_LEGGINGS
                displayName = "${RESET}Armor"
                enchantRoute(ArmorRoute)
            }
            i(4, 0) {
                material = Material.TNT
                displayName = "${RESET}Utility"
                enchantRoute(UtilRoute)
            }
        }
    }
}

class BuyNav(app: AppRoot, buyConfig: BuyConfig, player: Player) : RootComponent(app) {
    private var route: BuyRoute by prop(HomeRoute)

    private val nav: NavBar

    private val woolColor: Short

    init {
        val team = player.scoreboard.getEntryTeam(player.name)
                ?: throw IllegalStateException("${player.name} has no team")
        val teamInfo = buyConfig.teamInfos[team.name]
                ?: throw IllegalStateException("$team does not exist in teamInfos")
        woolColor = teamInfo.woolColor
        nav = NavBar(app) { route = it }
    }

    private val buyHome = BuyHome(app, woolColor, buyConfig, player)
    private val buyBlock = BuyBlock(app, woolColor, buyConfig, player)
    private val buyTools = BuyTools(app, buyConfig, player)
    private val buyArmor = BuyArmor(app, buyConfig, player)
    private val buyUtil = BuyUtil(app, buyConfig, player)

    override fun render(): RootElement {
        return root(container {
            // frame along the sides
            for (i in 0 until 9) {
                for (j in 0 until 6) {
                    i(i, j) {
                        material = Material.STAINED_GLASS_PANE
                        displayName = RESET
                        damage = WOOL_YELLOW
                        onTick { tick ->
                            if (tick % 6 < 3) {
                                itemBuilder {
                                    material = Material.STAINED_GLASS_PANE
                                    displayName = RESET
                                    damage = WOOL_YELLOW
                                }
                            } else {
                                itemBuilder {
                                    material = Material.STAINED_GLASS_PANE
                                    displayName = RESET
                                    damage = WOOL_RED
                                }
                            }
                        }
                    }
                }
            }

            c(1, 0) {
                h(nav.render(route))
            }

            c(1, 2) {
                h(when (route) {
                    HomeRoute -> buyHome.render()
                    BlockRoute -> buyBlock.render()
                    ToolRoute -> buyTools.render()
                    ArmorRoute -> buyArmor.render()
                    UtilRoute -> buyUtil.render()
                })
            }
        }) {
            title = "Buy"
            topSize = 6
            enableBottom = false
        }
    }
}

class BuyHome(app: AppRoot, woolColor: Short, buyConfig: BuyConfig, player: Player) : Component(app), Renderable {
    private val wool = PurchasableItem(app, buyConfig, player, itemBuilder {
        material = Material.WOOL
        damage = woolColor
        amount = 16
    }, buyConfig.woolCost)

    override fun render(): ViewElement {
        return container {
            h(wool.render())
        }
    }
}

class BuyBlock(app: AppRoot, woolColor: Short, buyConfig: BuyConfig, player: Player) : Component(app), Renderable {
    private val wool = PurchasableItem(app, buyConfig, player, itemBuilder {
        material = Material.WOOL
        damage = woolColor
        amount = 16
    }, buyConfig.woolCost)

    private val endStone = PurchasableItem(app, buyConfig, player, bedwars.endStone, buyConfig.endStoneCost)

    override fun render(): ViewElement {
        return container {
            c(0, 0) {
                h(wool.render())
            }
            c(1, 0) {
                h(endStone.render())
            }
        }
    }
}
