import { NbMenuItem } from '@nebular/theme';

export const MENU_ITEMS: NbMenuItem[] = [
  {
    title: 'Dashboard',
    icon: 'layers-outline',
    link: '/pages/dashboard',
    home: true,
  },
  {
    title: 'E-commerce exemplo',
    icon: 'shopping-cart-outline',
    link: '/pages/dashboard-old',
  },
  {
    title: 'Relatórios',
    icon: 'file-text-outline',
    link: '/pages/ui-features',
  },
  {
    title: 'Atendimentos',
    icon: 'settings-2-outline',
    children: [
      {
        title: 'Criar Atendimentos',
        link: '/pages/layout/stepper',
      },
      {
        title: 'Listar Atendimentos',
        link: '/pages/layout/list',
      },
    ],
  },
  {
    title: 'Clientes',
    icon: 'people-outline',
    children: [
      {
        title: 'Criar Cliente',
        link: '/pages/clientes/criar',
      },
      {
        title: 'Listar Clientes',
        link: '/pages/clientes/listar',
      },
    ],
  },
  {
    title: 'Produtos',
    icon: 'car-outline',
    children: [
      {
        title: 'Criar Produto',
        link: '/pages/produtos/criar',
      },
      {
        title: 'Listar Produto',
        link: '/pages/produtos/listar',
      },
    ],
  },
  {
    title: 'Marcas',
    icon: 'award-outline',
    children: [
      {
        title: 'Criar Marca',
        link: '/pages/marcas/criar',
      },
      {
        title: 'Listar Marca',
        link: '/pages/marcas/listar',
      },
    ],
  },
  {
    title: 'Serviços',
    icon: 'flash-outline',
    children: [
      {
        title: 'Criar Serviço',
        link: '/pages/ui-features/grid',
      },
      {
        title: 'Listar Serviços',
        link: '/pages/ui-features/icons',
      },
    ],
  },
  {
    title: 'Equipamentos',
    icon: 'umbrella-outline',
    children: [
      {
        title: 'Criar Equipamento',
        link: '/pages/ui-features/grid',
      },
      {
        title: 'Listar Equipamentos',
        link: '/pages/ui-features/icons',
      },
    ],
  },
  {
    title: 'Estoque',
    icon: 'archive-outline',
    children: [
      {
        title: 'Entrada Produtos',
        link: '/pages/ui-features/grid',
      },
      {
        title: 'Listar Entrada de Produtos',
        link: '/pages/ui-features/icons',
      },
      {
        title: 'Listar Saída de Produtos',
        link: '/pages/ui-features/icons',
      },
    ],
  },
  {
    title: 'Administrativo',
    icon: 'shield-outline',
    children: [
      {
        title: 'Criar Usuário',
        link: '/pages/extra-components/calendar',
      },
      {
        title: 'Criar Regra',
        link: '/pages/extra-components/progress-bar',
      },
      {
        title: 'Editar Permissão',
        link: '/pages/extra-components/spinner',
      },
    ],
  },
];
