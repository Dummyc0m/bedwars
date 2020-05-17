package bedwars

import com.dummyc0m.pylon.app.component.AppRoot
import com.dummyc0m.pylon.app.component.Component
import com.dummyc0m.pylon.app.component.Renderable
import com.dummyc0m.pylon.app.view.ViewElement
import com.dummyc0m.pylon.app.view.container
import org.bukkit.entity.Player

class BuyUtil(app: AppRoot, buyConfig: BuyConfig, player: Player) : Component(app), Renderable {
    private val pearlItem = PurchasableItem(app, buyConfig, player, enderPearl, buyConfig.enderPearlCost)
    private val fireballItem = PurchasableItem(app, buyConfig, player, fireball, buyConfig.fireballCost)
    private val tntItem = PurchasableItem(app, buyConfig, player, tnt, buyConfig.tntCost)
    private val appleItem = PurchasableItem(app, buyConfig, player, goldenApple, buyConfig.goldenAppleCost)
    private val bucketItem = PurchasableItem(app, buyConfig, player, waterBucket, buyConfig.waterBucketCost)

    override fun render(): ViewElement {
        return container {
            c(0, 0) {
                h(pearlItem.render())
            }
            c(1, 0) {
                h(fireballItem.render())
            }
            c(2, 0) {
                h(tntItem.render())
            }
            c(3, 0) {
                h(appleItem.render())
            }
            c(4, 0) {
                h(bucketItem.render())
            }
        }
    }
}