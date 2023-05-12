package ibf2022.batch2.csf.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;

@Service
public class ArchiveService {
    
    @Autowired private ArchiveRepository aRepo;
}
