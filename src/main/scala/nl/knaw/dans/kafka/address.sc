import java.net.InetSocketAddress

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.common.config.ConfigException
import org.apache.kafka.common.utils.Utils.{ getHost, getPort }

val url = "test.dans.knaw.nl:8080"

if (url != null && !url.isEmpty) try {
  val host = getHost(url)
  val port = getPort(url)
  if (host == null || port == null) throw new ConfigException("Invalid url in " + CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG + ": " + url)
  val address = new InetSocketAddress(host, port)
  if (address.isUnresolved)
    println("Removing server {} from {} as DNS resolution failed for {}", url, CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, host)
  else println(s"found: $address")
} catch {
  case e: IllegalArgumentException =>
    throw new ConfigException("Invalid port in " + CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG + ": " + url)
}
