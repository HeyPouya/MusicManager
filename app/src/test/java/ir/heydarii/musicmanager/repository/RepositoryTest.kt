package ir.heydarii.musicmanager.repository

import ir.heydarii.musicmanager.repository.local.AlbumsDAO
import ir.heydarii.musicmanager.repository.network.RetrofitMainInterface
import org.junit.Before
import org.mockito.Mock

class RepositoryTest {

    private lateinit var repository: Repository

    @Mock
    private lateinit var network: RetrofitMainInterface

    @Mock
    private lateinit var local: AlbumsDAO

    @Before
    fun setUp() {
        repository = Repository(network, local)
    }


}